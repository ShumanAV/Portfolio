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

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;
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
    public Optional<Price> findById(Integer priceId) {
        return priceRepository.findById(priceId);
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
    Метод сохраняет новый прайс в БД
     */
    @Transactional
    public void create(Price newPrice) {

        //записываем в прайс дату и время создания и изменения
        newPrice.setCreatedAt(LocalDateTime.now());
        newPrice.setUpdatedAt(LocalDateTime.now());

        //сохраняем новый прайс
        priceRepository.save(newPrice);
    }

    /*
    Метод сохраняет измененный прайс в БД, находим существующий прайс в БД по id,
    из измененного прайса копируем все поля, которые не null в существующий прайс, обновляем дату и время изменения
     */
    @Transactional
    public void update(Price updatedPrice) {

        //находим существующий прайс в БД по id
        Price existingPrice = priceRepository.findById(updatedPrice.getId()).get();

        //копируем значения всех полей кроме тех, которые null, из измененного прайса в существующий прайс
        copyNonNullProperties(updatedPrice, existingPrice);

        //обновляем дату и время изменения
        existingPrice.setUpdatedAt(LocalDateTime.now());
    }

    /*
    Метод добавляет вызов врача в список вызовов для прайса указанного в вызове, делается это для кэша
     */
    public void addCallingAtListForPrice(Calling calling, Price price) {
        addObjectOneInListForObjectTwo(calling, price, this);
    }
}
