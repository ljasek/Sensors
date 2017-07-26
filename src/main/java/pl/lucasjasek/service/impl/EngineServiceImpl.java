package pl.lucasjasek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.Engine;
import pl.lucasjasek.repository.EngineRepository;
import pl.lucasjasek.service.EngineService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EngineServiceImpl implements EngineService {

    private Logger logger = LoggerFactory.getLogger(EngineServiceImpl.class);

    private final EngineRepository engineRepository;

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository) {
        this.engineRepository = engineRepository;
    }

    @Override
    public List<String> getIncorrectlyEngine(int pressureTreshold, int tempTreshold) {

        List<Engine> engineList = engineRepository.getEnginesWithIncorrectlyPressure(pressureTreshold);

        if (engineList.isEmpty()) {
            logger.info("*****-----     all engines work properly    -----*****");
            return Collections.emptyList();
        }

        return engineList.stream()
                .map(Engine::getTemperatureSensors)
                .flatMap(List::stream)
                .filter(s -> s.getValue() >= tempTreshold)
                .map(s -> s.getEngine().getEngineId())
                .distinct()
                .collect(Collectors.toList());
    }
}