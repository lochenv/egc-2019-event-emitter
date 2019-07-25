# egc-2019-event-emitter

## Description
This program has been made to handle the authentication of user and notify people of their opponent and table number after pairing on MacMahon file.
This is only the backend part, there is a front-end part developped with Angular that you can find here : https://github.com/lochenv/egc-2019-event-web

## Famework
This software is base on the Spring Boot framework running under a Java 8 JVM. Other libraries as Apaches libraries are used as utilities libraries.

## API Description
This is a REST implementation. The application consume and produce JSON.
This is the High level documentation, full documentaton can be found in the WiKi

### Authentication
| Action        | HTTP Call                           | Parameters                | Response                    | Return code                   |
|---------------|-------------------------------------|---------------------------|-----------------------------|------------------------------|
| **Authenticate**  | POST <root>/api/authenticate        | UserAuthenticationRequest | UserAuthenticationResponse  | OK : 200 / Unauthorized : 401 |
| **Validate JWT**  | POST <root>/api/authenticate/valid  | JwtValidateRequest        | JwtValidateReponse          | OK : 200 / Unauthorized : 401 |

### Notify round
| Action      | HTTP Call                    | Parameters    | Response          | Return code                         |
|-------------|------------------------------|---------------|-------------------|-------------------------------------|
| **Notify**  | POST <root>/api/notify-round | MultipartFile | SendPairingReport | OK : 200 / Cannot parse file : 412  | 

## License
https://github.com/lochenv/egc-2019-event-emitter/blob/master/LICENCE.md
