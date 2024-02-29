#Autor: Ronald Carrasco

Feature: To test the endpoint of the aplication

  Como customer service
  Quiero que primero genere un id de sesion
  Para controlar el registro de usuarios

  Background: Setup the BasePath
    * url 'https://ext_api.acity.com.pe'
    * header Accept = 'application/json'
    * header Content-Type = 'application/json'

  @ValidarGeneracionIdSesion
  Scenario Outline: caso de prueba <nombreCaso>
    Given path '/api-con-registro_invitado/api/v3/RegistroInvitado/GenerarSesionRegistro'
    And request
    """
      {
        "apiKey": "<apikey>",
        "clienteApp": "<clientApp>",
        "origen": "<origen>"
      }
    """
    When method POST
    Then status 200
    And print response

    Examples:
      | nombreCaso                      | apikey                                               | clientApp  | origen     |
      | Generar Id de Sesion de registro| MjFkNzNlODgtMTk5My00YmYzLWIzN2EtOTc1NjU5NTc0OTJjQCs6 | WebCalimaco| WebCalimaco|


