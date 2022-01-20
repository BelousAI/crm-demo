Spring Web Application: CRM-demo
====================================================================

Используемые технологии / инструменты / фреймворки:\
Servlet API, JSP/ Spring 5: MVC, AOP, Security/ Hibernate API/ MySQL/ Maven

### Другие проекты:
- [Web App: Spring Boot 2 + Thymeleaf + Spring Security + Spring Data JPA](https://github.com/BelousAI/spring-boot-web-employee-directory)
- [RESTful service: Regular Spring REST + native Hibernate API](https://github.com/BelousAI/spring-crm-rest)
- [RESTful service: Spring Boot REST + JPA(Hibernate)](https://github.com/BelousAI/employee-directory-rest-service)

### Функциональность приложения:
- создание новых клиентов;
- редактирование и удаление существующих;
- поиск клиентов по имени или фамилии;
- сортировка клиентов по заголовку колонки;
- регистрация нового пользователя системы;
- аутентификация и авторизация пользователя;
- отображение контента в зависимости от роли пользователя

### Управление доступом на основе ролей:
- **Employee role:** просмотр списка клиентов;
- **Manager role:** *Employee role* + добавление новых и изменение существующих клиентов;
- **Admin role:** *Manager role* + удаление клиентов

### Тестовые учетки пользователей:
1. *john*
   - **user id**: john
   - **password:** fun123
   - **Role(s):** ROLE_EMPLOYEE
2. *mary*
   - **user id:** mary
   - **password:** fun123
   - **Role(s):** ROLE_EMPLOYEE, ROLE_MANAGER
3. *susan*
   - **user id:** susan
   - **password:** fun123
   - **Role(s):** ROLE_EMPLOYEE, ROLE_MANAGER, ROLE_ADMIN