package models.lombok;

import lombok.Data;

// объявляем аннотацию '@Data', объявляем переменные
// далее проверяем наличие плагина через 'Preferences' - 'Plugins' - 'Lombok'
// плагин 'Lombok' должен быть установлен
@Data
public class LoginBodyLombokModel {
    public String email, password;
}
