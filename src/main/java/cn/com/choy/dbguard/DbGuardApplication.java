package cn.com.choy.dbguard;

import cn.com.choy.dbguard.service.McpService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DbGuardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbGuardApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider dbMcpService(McpService mcpService) {
		return MethodToolCallbackProvider.builder().toolObjects(mcpService).build();
	}

}
