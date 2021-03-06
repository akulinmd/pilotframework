# language: ru
@TEST
Функционал: Тестовый сценарий

  @0001
  Сценарий: Инициализация данных
    И пользователь устанавливает переменные:
      | Инфо для поиска | Java 8 |

  @0002
  Сценарий: Тестовый сценарий
    И пользователь переходит на стартовую страницу
    И открывается страница "Яндекс"
    И пользователь заполняет поля значениями:
      | Строка поиска | #{Инфо для поиска} |
    И пользователь нажимает кнопку "Найти"