package com.yudiol.configuration;

import com.yudiol.annotation.Configuration;
import com.yudiol.annotation.Instance;
import com.yudiol.controller.HelperController;
import com.yudiol.repository.HelperRepository;
import com.yudiol.service.HelperService;
import com.yudiol.service.impl.HelperServiceImpl;

@Configuration
public class SupportConfiguration {

    @Instance
    public HelperRepository helperRepository() {
        return new HelperRepository();
    }

    @Instance
    public HelperService helperService(HelperRepository helperRepository) {
        return new HelperServiceImpl(helperRepository);
    }

    @Instance
    public HelperController helperController(HelperService helperService) {
        return new HelperController(helperService);
    }
}
