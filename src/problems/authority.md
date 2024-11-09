## how to check if the user has authority to access the hotel?
1. use annotation to mark a request parameter `@HotelAccess @PathVariable("hotelId") String hotelId`
2. use interceptor to check request uri `/hotels/{hotelId}`
3. check and throw `noPrincipalException` in the service layer.
4. check and throw `noPrincipalException` in the endpoint layer.


## hotel 与 booking 之间的数据共享
1. hotel -> rmq(event) -> listener(fetch from hotel api) -> mongodb
2. hotel -> rmq(dto) -> listener(fetch from dto messsage) -> mongodb
3. hotel -> mysql -> sync(fetch from binlog) -> mysql/mongodb
