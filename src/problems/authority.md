## how to check if the user has authority to access the hotel?
1. use annotation to mark a request parameter `@HotelAccess @PathVariable("hotelId") String hotelId`
2. use interceptor to check request uri `/hotels/{hotelId}`
3. check and throw `noPrincipalException` in the service layer.
4. check and throw `noPrincipalException` in the endpoint layer.
