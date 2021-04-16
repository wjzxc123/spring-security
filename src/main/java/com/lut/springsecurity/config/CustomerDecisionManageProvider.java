package com.lut.springsecurity.config;



import java.util.function.Function;
import com.lut.springsecurity.bean.Url;
import org.springframework.security.access.AccessDecisionManager;

/**
 * Describe:
 *
 * @author Licon
 * @date 2021/4/2 10:31
 */
public class CustomerDecisionManageProvider implements IDecisionManageProvider{
	private final Function<Integer,AccessDecisionManager> decisionManageProvider;

	public CustomerDecisionManageProvider(Function<Integer, AccessDecisionManager> decisionManageProvider) {
		this.decisionManageProvider = decisionManageProvider;
	}

	@Override
	public AccessDecisionManager getAccessDecisionManager(Integer voteType) {
		return this.decisionManageProvider.apply(voteType);
	}
}
