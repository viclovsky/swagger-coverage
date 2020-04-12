menu.summary=Сводка
menu.operations=Методы
menu.tags=Группы
menu.branch=Варианты
menu.generation=Об отчёте

common.state.full=Полностью
common.state.partial=Частично
common.state.empty=Не покрыто
common.state.no_call=Пропущено

operations.all=Все
operations.full=Полностью
operations.partial=Частично
operations.empty=Не покрыто
operations.no_call=Пропущено
operations.missed=Лишние запросы

summary.operations=Сводка по методам
summary.operations.all=Всего
summary.operations.no_call=Пропущено
summary.operations.missed=Лишние запросы

summary.branches=Сводка по вариантам
summary.branches.total=Всего
summary.branches.covered=Покрыто
summary.branches.uncovered=Не покрыто

summary.tags=Сводка по группам
summary.tags.all=Всего
summary.tags.no_call=Пропущено

badged.full=Полностью покрыто
badged.partial=Частично покрыто
badged.empty=Не покрыто

details.operation.calls=вызов(ов)
details.operation.no_data=Нет данных
details.operation.status=Статус ответа
details.operation.parameters=Параметры
details.operation.parameter.type=Тип
details.operation.parameter.name=Название
details.operation.parameter.value=Значения
details.branchlist.name=Вариант
details.branchlist.details=Подробности
details.branchprogress.postfix=вариантов покрыто

details.tag.operations=методов
details.branch.operation=Метод
details.branch.branchname=Вариант
details.branch.details=Подробности

generation.configuration=Настройки
generation.parsed_file_count=Проверено файлов с результатами
generation.time=Затраченое на отчёт время
generation.result_file_created_interval=Файлы с результатами созданы
generation.report.date=Дата генерации отчёта

summary.tag.legend=Распределение статусов покрытия групп
summary.operations.legend=Распределение статусов покрытия операций

predicate.ParameterValueConditionPredicate.name=Все значения параметра
predicate.ParameterValueConditionPredicate.description=Проверка, что метод вызван со всеми описанным в enum параметра
predicate.NotOnlyParameterListValueConditionPredicate.name=Значения не только из списка
predicate.NotOnlyParameterListValueConditionPredicate.description=Проверка, что метод вызывался со значением, которое не описано в enum параметра
predicate.DefaultBodyConditionPredicate.name=Наличие тела запроса
predicate.DefaultBodyConditionPredicate.description=Проверка, что тело запроса было не пустым
predicate.DefaultStatusConditionPredicate.name=Статус ответа
predicate.DefaultStatusConditionPredicate.description=Проверка, что есть ответ с описанным  в responses http-статусом
predicate.DefaultParameterConditionPredicate.name=Проверка значения параметра
predicate.DefaultParameterConditionPredicate.description=Проверка, что параметр пустой или не пустой
predicate.FullStatusConditionPredicate.name=Только описанные статусы
predicate.FullStatusConditionPredicate.description=Проверка, что не было получено статусов, которые не описаны