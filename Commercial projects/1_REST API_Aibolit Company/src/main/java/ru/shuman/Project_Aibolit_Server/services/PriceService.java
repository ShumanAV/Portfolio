package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.models.Price;
import ru.shuman.Project_Aibolit_Server.repositories.PriceRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

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
    Метод сохраняет поля из измененного прайса в существующий в БД, находим существующий прайс в БД,
    из измененного прайса переносим все поля, которые не null в существующий прайс, обновляем дату и время изменения
    и сохраняем обновленный существующий прайс
     */
    @Transactional
    public void update(Price price) {

        Price existingPrice = priceRepository.findById(price.getId()).get();

        copyNonNullProperties(price, existingPrice);

        existingPrice.setUpdatedAt(LocalDateTime.now());
    }

    /*
    Метод добавляет вызов врача в лист прайса, делается это для кэша
     */
    public void addCallingAtListForPrice(Calling calling, Price price) {
        GeneralMethods.addObjectOneInListForObjectTwo(calling, price, this);
    }
}
