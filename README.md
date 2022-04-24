TO DO:
* handle DuplicateKeyException with 409 Conflict, or retry request
* integration tests for POST /deploy endpoint for 400, 415, 409 codes
* handle missing system version with 404 Not Found
* integration tests for GET /services endpoint for 404 code
* unit tests for ServiceDeployment validation:
    * version >= 1
    * services can be downgraded
* a test case where a previous service version is deployed; system version should be incremented
* cache current version
