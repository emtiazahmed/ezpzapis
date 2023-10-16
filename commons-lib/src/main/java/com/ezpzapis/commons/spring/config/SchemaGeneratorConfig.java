package com.ezpzapis.commons.spring.config;

import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemaGeneratorConfig {
    @Bean
    public SchemaGenerator schemaGenerator() {
        JacksonModule jacksonModule = new JacksonModule();
        JakartaValidationModule jakartaValidationModule =
                new JakartaValidationModule(JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS,
                        JakartaValidationOption.NOT_NULLABLE_FIELD_IS_REQUIRED);
        SchemaGeneratorConfigBuilder configBuilder =
                new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09, OptionPreset.PLAIN_JSON)
                        .with(jacksonModule)
                        .with(jakartaValidationModule)
                        .with(Option.SCHEMA_VERSION_INDICATOR,
                                Option.MAP_VALUES_AS_ADDITIONAL_PROPERTIES,
                                Option.EXTRA_OPEN_API_FORMAT_VALUES);
        com.github.victools.jsonschema.generator.SchemaGeneratorConfig config = configBuilder.build();
        return new SchemaGenerator(config);
    }
}
