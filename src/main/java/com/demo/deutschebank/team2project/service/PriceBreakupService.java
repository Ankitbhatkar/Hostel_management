package com.demo.deutschebank.team2project.service;

import com.demo.deutschebank.team2project.dto.PriceBreakupDto;

public interface PriceBreakupService {

    PriceBreakupDto getPriceBreakup(int bedId,int durationInMonth);
}
