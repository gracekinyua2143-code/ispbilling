package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.Station;
import com.leavemanagementsystem.leave.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    // CREATE
    public Station createStation(Station station){
        return stationRepository.save(station);
    }

    // READ
    public List<Station> getStations(){
        return stationRepository.findAll();
    }

    public Station getById(Long id){
        return stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Station not found"));
    }

    // UPDATE
    public Station updateStation(Long id, Station updatedStation){

        Station station = getById(id);

        station.setName(updatedStation.getName());

        return stationRepository.save(station);
    }

    // DELETE
    public void deleteStation(Long id){
        stationRepository.deleteById(id);
    }

}