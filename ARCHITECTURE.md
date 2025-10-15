# Weather App - MVVM + Clean Architecture

## Архитектура проекта

Проект реорганизован по принципам **MVVM** и **Clean Architecture** для улучшения поддерживаемости, тестируемости и масштабируемости.

### Структура папок

```
app/src/main/java/com/example/weatherappcompose/
├── data/                           # Data Layer
│   ├── datasource/                 # Источники данных
│   │   └── WeatherRemoteDataSource.kt
│   ├── mapper/                     # Мапперы для преобразования данных
│   │   └── WeatherMapper.kt
│   └── repository/                 # Реализации репозиториев
│       └── WeatherRepositoryImpl.kt
├── domain/                         # Domain Layer
│   ├── models/                     # Модели данных
│   │   └── WeatherModel.kt
│   ├── repository/                 # Интерфейсы репозиториев
│   │   └── WeatherRepository.kt
│   └── usecase/                    # Бизнес-логика
│       └── WeatherUseCases.kt
├── presentation/                   # Presentation Layer
│   ├── ui/                         # UI компоненты
│   │   ├── WeatherList.kt
│   │   ├── WeatherScreen.kt
│   │   └── SearchDialog.kt
│   └── viewmodel/                  # ViewModels
│       └── WeatherViewModel.kt
├── di/                            # Dependency Injection
│   └── AppModule.kt
└── ui/theme/                      # UI темы
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

### Основные компоненты

#### 1. Domain Layer
- **WeatherModel**: Модель данных с оптимизированными свойствами
- **WeatherRepository**: Интерфейс репозитория
- **WeatherUseCases**: Бизнес-логика приложения

#### 2. Data Layer
- **WeatherRemoteDataSource**: Источник данных (API)
- **WeatherMapper**: Преобразование JSON в модели
- **WeatherRepositoryImpl**: Реализация репозитория

#### 3. Presentation Layer
- **WeatherViewModel**: ViewModel с состоянием UI
- **WeatherScreen**: Главный экран
- **WeatherList**: Оптимизированный список погоды
- **SearchDialog**: Диалог поиска

#### 4. Dependency Injection
- **Hilt**: Для внедрения зависимостей
- **AppModule**: Модуль DI

### Преимущества новой архитектуры

1. **Разделение ответственности**: Каждый слой имеет свою роль
2. **Тестируемость**: Легко тестировать каждый компонент отдельно
3. **Поддерживаемость**: Код организован и понятен
4. **Масштабируемость**: Легко добавлять новые функции
5. **Производительность**: Оптимизированные компоненты UI

### Использование

Приложение использует:
- **MVVM** паттерн для UI
- **Clean Architecture** для структуры
- **Hilt** для DI
- **Coroutines** для асинхронности
- **StateFlow** для реактивности
