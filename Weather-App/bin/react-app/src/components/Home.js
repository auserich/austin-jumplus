import React, {useState, useEffect} from 'react'
import { useAppContext } from "../lib/contextLib";
import Container from "react-bootstrap/Container";
import Card from 'react-bootstrap/Card';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';
import "./Home.css";
import { Button, ButtonGroup } from 'react-bootstrap';

const Home = () => {

  const { isAuthenticated, token, user } = useAppContext();
  const [ currentIndex, setCurrentIndex ] = useState(0);
  const [ currentLocation, setCurrentLocation ] = useState([]);
  const [ response, setResponse ] = useState([]);
  const [ forecast, setForcast ] = useState([]);
  const [ hourly, setHourly ] = useState([]);  
  const [ currentTemp, setCurrentTemp] = useState([]);
  const [ feelsLike, setFeelsLike ] = useState([]);
  const [ mintemp, setMinTemp ] = useState([])
  const [ maxtemp, setMaxTemp ] = useState([]);
  const [ dropTitle, setDropTitle ] = useState("");
  const [ locationSet, setLocationSet ] = useState([])
  const [ setUp, setSetUp ] = useState(false);
  const [ message, setMessage ] = useState("")
  
  useEffect(()=>{
    if (isAuthenticated) {
      handleButton()
    }

  },[])

  async function fetchData(index) {
    const fjson = require('../secrets/secrets.json');
    const zipcode = user.savedLocation[index].location.zipcode;
    const uri = "http://api.weatherapi.com/v1/forecast.json?key=" + fjson.weather_api_key + "&q=" + zipcode + "&days=1&aqi=no&alerts=no";
    return fetch(uri, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
    })
    .then(data =>  {
     if (data.ok) {
       return data.json();
     }
     throw new Error("WRONG USERNAME AND PASSWORD");
   })
    .catch((error) => {
     console.log(error);
   });
  }

  async function getMessage(weather) {
    return fetch('http://localhost:8080/api/message', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token.jwt
      },
      body: JSON.stringify(weather)
    })
    .then(data =>  {
     if (data.ok) {
       return data.text();
     }
     throw new Error("Something went wrong");
   })
    .catch((error) => {
     console.log(error);
   });
  }

  async function handleButton() {
    //event.preventDefault();

    try {
      const response1 = await fetchData(currentIndex);
      setCurrentLocation(user.savedLocation[currentIndex].location)
      const locationSetupSet = [];
      let index = 0;
      user.savedLocation.forEach(getLocations)
      function getLocations(locationObj) {
        let tempObj = {
          "index": index,
          "city": locationObj.location.city,
          "zipcode": locationObj.location.zipcode,
          "title": locationObj.location.city + ", " + locationObj.location.zipcode
        }
        locationSetupSet.push(tempObj)
        index += 1;
      }
      const messageObj = {
        "temp": parseFloat(response1.current.temp_f),
        "precipitation": null
      }
      const message1 = await getMessage(messageObj);
      setMessage(message1)
      setLocationSet(locationSetupSet)
      setDropTitle(user.savedLocation[currentIndex].location.city + ", " + user.savedLocation[currentIndex].location.zipcode)
      setResponse(response1)
      setForcast(response1.forecast.forecastday)
      setHourly(response1.forecast.forecastday[0].hour)
      setCurrentTemp(response1.current.temp_f)
      setFeelsLike(response1.current.feelslike_f)
      setMaxTemp(response1.forecast.forecastday[0].day.maxtemp_f)
      setMinTemp(response1.forecast.forecastday[0].day.mintemp_f)
      setSetUp(true)
      console.log(response1.current.temp_f.toString())
    } catch (e) {
      console.log(e);
    }
  }

  async function updateWeather(updateLocation) {
    try {
      const response1 = await fetchData(updateLocation.index);
      const messageObj = {
        "temp": parseFloat(response1.current.temp_f),
        "precipitation": null
      }
      const message1 = await getMessage(messageObj);
      setMessage(message1)
      setCurrentLocation(user.savedLocation[updateLocation.index].location)
      
      setResponse(response1)
      setForcast(response1.forecast.forecastday)
      setHourly(response1.forecast.forecastday[0].hour)
      setCurrentTemp(response1.current.temp_f)
      setFeelsLike(response1.current.feelslike_f)
      setMaxTemp(response1.forecast.forecastday[0].day.maxtemp_f)
      setMinTemp(response1.forecast.forecastday[0].day.mintemp_f)
      setSetUp(true)
      console.log(response1.current.temp_f.toString())
    } catch (e) {
      console.log(e);
    }
  }
  

  function renderLander() {
    return (
      <div className="lander">
        <h1>Weather Dashboard</h1>
        <p className="text-muted">A personalized weather application</p>
        <br/>
        <br/>
        <p>Please Login to access dashboard</p>
      </div>
    );
  }

  function renderDashboard() {
    return (
      <div>
      <Container className="rendered">
      {/* <Container className="rendered"> */}
        <ButtonGroup>
        <DropdownButton className="spacing"
        variant="success"
        title={dropTitle}>
          {locationSet.map(
            (label) => (
              <Dropdown.Item value={label.title} eventKey={label.index} disabled={label.index == currentIndex ? true : false}
              onClick={(e) => {setDropTitle(label.title); setCurrentIndex(label.index); updateWeather(label);}}>{label.title}</Dropdown.Item>
            ),
          )}
        </DropdownButton>
        
        </ButtonGroup>
        
        <Card className="text-center"
        border="success">
          <Card.Header>{dropTitle}</Card.Header>
          <Card.Body>
            <Card.Title>{JSON.stringify(currentTemp, null, 2)}{'\u00B0'}{'F'}</Card.Title>
            <Card.Text>{JSON.stringify(message, null, 2)}</Card.Text>
            <Card.Text>{'Feels like: '}{feelsLike}{'\u00B0'}{'F'}</Card.Text>
            <Card.Text>{'Todays min/max: '}{mintemp}{'\u00B0'}{'F'}{' | '}{maxtemp}{'\u00B0'}{'F'}</Card.Text>
          </Card.Body>
        </Card>
      </Container>
      </div>
    )
  }

  return (
    <div className="Home">
        {isAuthenticated && setUp ? renderDashboard(): renderLander()}
      </div>

  )
}

export default Home