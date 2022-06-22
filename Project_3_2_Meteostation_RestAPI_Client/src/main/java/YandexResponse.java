import java.util.List;

// этот класс будет соответствовать структуре всего json файла, который присылает Яндекс, в нем массив с именем translations
public class YandexResponse {

    private List<Translation> translations;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
