@max
Feature: Example

  Scenario Outline: Try it now! <name>
    When GET https://pokeapi.co/api/v2/pokemon/%s <name> для примера
      | Path                      | Value    |
      | abilities.ability[0].name | is:S     |
      | forms.name[0]             | S:<name> |
      | forms.url[0]              | is:S     |
    Examples:
      | name       |
      | ditto      |
      | bulbasaur  |
      | charmeleon |