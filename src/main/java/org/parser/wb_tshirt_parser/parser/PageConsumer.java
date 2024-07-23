package org.parser.wb_tshirt_parser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PageConsumer.class);

    @Value("${parser.user-agent}")
    private String userAgent;



}
