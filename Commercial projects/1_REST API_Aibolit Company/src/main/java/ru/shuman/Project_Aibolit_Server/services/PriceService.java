package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.models.Price;
import ru.shuman.Project_Aibolit_Server.repositories.PriceRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Optional<Price> findById(Integer diaryId) {
        return priceRepository.findById(diaryId);
    }

    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    public List<Price> findAllByPublished(Boolean published) {
        return priceRepository.findByPublished(published);
    }

    public void addCallingAtListForPrice(Calling calling, Price price) {
        GeneralMethods.addObjectOneInListForObjectTwo(calling, price, this);
    }

    @Transactional
    public void create(Price price) {

        price.setCreatedAt(LocalDateTime.now());
        price.setUpdatedAt(LocalDateTime.now());

        priceRepository.save(price);
    }

    @Transactional
    public void update(Price price) {

        price.setUpdatedAt(LocalDateTime.now());

        priceRepository.save(price);
    }
}
