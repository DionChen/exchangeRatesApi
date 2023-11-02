# exchangeRatesApi

## 功能簡述:
1. 呼叫 coindesk API
2. 呼叫coindesk API,並實作新的 API。此新 API 提供:
   - 更新時間(時間格式範例:1990/01/01 00:00:00)
   - 幣別相關資訊(幣別,幣別中文名稱,以及匯率)
3. 一張幣別與其對應中文名稱的資料表(需附建立 SQL 語法),具有 查詢 / 新增 / 修改 / 刪除 功能 API。

## Requirements

For building and running the application you need:

    JDK 1.8
    Maven 3

## Run Spring Boot application

```mvn spring-boot:run```

## Explore Rest APIs

Get currency by code
```
GET /api/v1.0/currency/{code}
```

Create currency
```
POST /api/v1.0/currency
```
body example
```
{
    "code": "TTE",
    "chName": "測試",
    "rate": 34459.8798,
    "description": "This is a test"
}
```
update currency by code

```
PUT /api/v1.0/currency/{code}
```

body example
```
{
    "chName": "update",
    "rate": 34459.1234,
    "description": "This is a update test"
}
```

delete currency(soft delete)
```
DELETE /api/v1.0/currency/{code}
```

call CoinDesk native Api

```
GET /api/v1.0/coindesk/native-api
```

call new Api

```
GET /api/v1.0/coindesk/transformed-api
```