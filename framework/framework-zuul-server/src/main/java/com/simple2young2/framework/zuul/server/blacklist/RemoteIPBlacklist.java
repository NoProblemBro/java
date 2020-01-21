package com.simple2young2.framework.zuul.server.blacklist;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVICE_ID_KEY;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.support.RibbonCommandContext;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommand;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.simple2young2.framework.zuul.server.conf.BlacklistRemoteProperties;

public class RemoteIPBlacklist implements IIPBlacklist {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(RemoteIPBlacklist.class);
	
	protected ProxyRequestHelper helper;
	protected RibbonCommandFactory<?> ribbonCommandFactory;
	protected List<RibbonRequestCustomizer> requestCustomizers;
	private BlacklistRemoteProperties properties;
	private ObjectMapper objectMapper;

	public RemoteIPBlacklist(
		ProxyRequestHelper helper, 
		RibbonCommandFactory<?> ribbonCommandFactory,
		List<RibbonRequestCustomizer> requestCustomizers,
		BlacklistRemoteProperties properties,
		ObjectMapper objectMapper
	) {
		super();
		this.helper = helper;
		this.ribbonCommandFactory = ribbonCommandFactory;
		this.requestCustomizers = requestCustomizers;
		this.properties = properties;
		this.objectMapper = objectMapper;
	}

	@Override
	public Set<String> findIpsInBlacklist() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		RibbonCommandContext commandCtx = buildCommandContext(context);
		RibbonCommand command = this.ribbonCommandFactory.create(commandCtx);
		ClientHttpResponse response = command.execute();
		try {
			InputStream inputStream = response.getBody();
			byte[] data = StreamUtils.copyToByteArray(inputStream);
			if(response.getStatusCode() == HttpStatus.OK) {
				return this.objectMapper.readValue(data, new TypeReference<Set<String>>() {});
			}else {
				throw new ZuulException(String.format("IP黑名单查询失败,IP黑名单服务返回：%s",new String(data)), response.getStatusCode().value(), "access denied");
			}
		}catch (ZuulException e) {
			throw e;
		}catch (Exception e) {
			throw new ZuulException(e, "IP黑名单查询异常", HttpStatus.INTERNAL_SERVER_ERROR.value(), "access denied");
		}
	}
	
	private RibbonCommandContext buildCommandContext(RequestContext context) {
		HttpServletRequest request = context.getRequest();
		MultiValueMap<String, String> headers = this.helper.buildZuulRequestHeaders(request);
		
		// 增加源服务id头
		String serviceId = (String) context.get(SERVICE_ID_KEY);
		if(!StringUtils.isEmpty(serviceId)) {
			headers.add(FilterConstants.SERVICE_ID_HEADER, serviceId);
		}
		
		return new RibbonCommandContext(
			this.properties.getServiceId(), 
			HttpMethod.GET.name(), 
			this.properties.getUri(), 
			this.properties.getRetryable(), 
			headers, 
			this.helper.buildZuulRequestQueryParams(request),
			null, 
			this.requestCustomizers
		);
	}
}
