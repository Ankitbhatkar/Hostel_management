package com.demo.deutschebank.team2project.serviceImpl;

import com.demo.deutschebank.team2project.Entity.Bed;
import com.demo.deutschebank.team2project.Repository.BedRepo;
import com.demo.deutschebank.team2project.dto.PriceBreakupDto;
import com.demo.deutschebank.team2project.service.PriceBreakupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PriceBreakupServiceImpl implements PriceBreakupService {

    @Autowired
    BedRepo bedRepo;

    @Override
    public PriceBreakupDto getPriceBreakup(int bedId, int durationInMonth) {

        Bed bed = bedRepo.findById(bedId).get();
        int finalPrice = bed.getPrice() * durationInMonth + bed.getDeposit();
        PriceBreakupDto priceBreakupDto = new PriceBreakupDto();
        priceBreakupDto.setBedId(bedId);
        priceBreakupDto.setBedNo(bed.getBedNo());
        priceBreakupDto.setRoomNo(bed.getRoom().getRoomNo());
        priceBreakupDto.setFloorNo(bed.getRoom().getFloor().getFloorNo());
        priceBreakupDto.setBuildingName(bed.getRoom().getFloor().getBuilding().getName());
        priceBreakupDto.setHostelName(bed.getRoom().getFloor().getBuilding().getHostel().getName());
        priceBreakupDto.setPrice(bed.getPrice());
        priceBreakupDto.setDeposit(bed.getDeposit());
        priceBreakupDto.setDurationInMonth(durationInMonth);
        priceBreakupDto.setFinalAmount(finalPrice);
        return priceBreakupDto;
    }
}
