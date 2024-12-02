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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    /*
    Метод ищет прайс по id и возвращает его в обертке Optional
     */
    public Optional<Price> findById(Integer diaryId) {
        return priceRepository.findById(diaryId);
    }

    /*
    Метод формирует и возвращает список всех прайсов
     */
    public List<Price> findAll() {
        return priceRepository.findAll();
    }

    /*
    Метод формирует и возвращает список всех прайсов с учетом флага published
     */
    public List<Price> findAllByPublished(Boolean published) {
        return priceRepository.findByPublished(published);
    }

    /*
    Метод сохраняет новый прайс, добавляет в него дату и время создания и изменения
     */
    @Transactional
    public void create(Price price) {

        price.setCreatedAt(LocalDateTime.now());
        price.setUpdatedAt(LocalDateTime.now());

        priceRepository.save(price);
    }

    /*
    Метод сохраняет измененный прайс, находим существующий прайс в БД, из него переносим дату создания в изменяемый
    прайс, обновляем дату изменения и сохраняем изменяемый прайс
     */
    @Transactional
    public void update(Price price) {

        Price existingPrice = priceRepository.findById(price.getId()).get();

        price.setCreatedAt(existingPrice.getCreatedAt());
        price.setUpdatedAt(LocalDateTime.now());

        priceRepository.save(price);
    }

    /*
    Метод добавляет вызов врача в лист прайса, делается это для кэша
     */
    public void addCallingAtListForPrice(Calling calling, Price price) {
        GeneralMethods.addObjectOneInListForObjectTwo(calling, price, this);
    }
}
