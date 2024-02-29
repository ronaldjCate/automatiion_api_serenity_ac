function fn() {
     karate.configure('connectTimeout', 60000);
     karate.configure('readTimeout', 60000);
     karate.configure('logPrettyResponse', true);

     var config = {
         baseUrl : 'https://jsonplaceholder.typicode.com/',
         usuario: '/users'
     }

     return config;
}