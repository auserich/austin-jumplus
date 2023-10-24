var fs = require("fs");
console.log("READING FROM A FILE");

fs.readFile('./secrets/secrets.txt', function (err, data) {
    const obj = JSON.parse(data);
    console.log("FILE READ: " + obj.weather_api_key);
})