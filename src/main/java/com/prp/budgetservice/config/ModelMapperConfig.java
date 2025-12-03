package com.prp.budgetservice.config;

import com.prp.budgetservice.dto.month.MonthBudgetResponseDTO;
import com.prp.budgetservice.dto.notification.NotificationResponseDTO;
import com.prp.budgetservice.entity.MonthBudget;
import com.prp.budgetservice.entity.Notification;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);


        mapper.typeMap(MonthBudget.class, MonthBudgetResponseDTO.class)
                .addMapping(
                        MonthBudget::getLocked,
                        MonthBudgetResponseDTO::setLocked
                );

        mapper.typeMap(Notification.class, NotificationResponseDTO.class)
                .addMapping(
                        Notification::getRead,
                        NotificationResponseDTO::setRead
                );

        return mapper;
    }
}

