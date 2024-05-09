package com.example.amongserver.component;

import com.example.amongserver.domain.entity.GameCoordinates;
import com.example.amongserver.reposirory.GameCoordinatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StartCoordinatesScript implements CommandLineRunner {
    private final GameCoordinatesRepository gameCoordinatesRepository;

    private final List<GameCoordinates> coordinatesList = new ArrayList<GameCoordinates>();



    public void addStartingCoordinates() {
        coordinatesList.add(new GameCoordinates(55.686896, 37.504162, false));
        coordinatesList.add(new GameCoordinates(55.686916, 37.496937, false));
        coordinatesList.add(new GameCoordinates(55.679843, 37.507568, false));
        coordinatesList.add(new GameCoordinates(55.690882, 37.504051, false));
        coordinatesList.add(new GameCoordinates(55.683772, 37.497699, false));
        coordinatesList.add(new GameCoordinates(55.682802, 37.502332, false));
        coordinatesList.add(new GameCoordinates(55.684439, 37.505973, false));
        coordinatesList.add(new GameCoordinates(55.679527, 37.503378, false));
        coordinatesList.add(new GameCoordinates(55.688928, 37.500681, false));
        coordinatesList.add(new GameCoordinates(55.684419, 37.502729, false));
        coordinatesList.add(new GameCoordinates(55.8010271,37.8057306,false));
    }

    @Override
    public void run(String... args) {
        addStartingCoordinates();
        gameCoordinatesRepository.saveAll(coordinatesList);
    }
}
