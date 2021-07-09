package br.com.viniculaubots.lojavinhoapi.config;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SweggerConfig {

	private static final String apiPakege = "br.com.viniculaubots.lojavinhoapi";
	
	public Docket atualizadorVinhoVendido() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName("1")
				.select()
				.apis(RequestHandlerSelectors.basePackage(apiPakege))
				.build()
				.apiInfo(apiInfo("1"));
	}

	private ApiInfo apiInfo(String versao) {
		return new ApiInfoBuilder().title("Atualizador Vinho Vendidos")
				.description("Atualizador Vinho Vendidos")
				.version(versao)
				.build();
	}
}