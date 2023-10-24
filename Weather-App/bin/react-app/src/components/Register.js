import React, { useState } from 'react'
import './Register.css';
import LoaderButton from "../components/LoaderButton";
import { onError } from "../lib/errorLib";
import Form from "react-bootstrap/Form";
import {postcodeValidator} from "postcode-validator";
import { useAppContext } from "../lib/contextLib";

export default function Register() {

    const [isLoading, setIsLoading] = useState(false);
    const { userRegistered } = useAppContext();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("ROLE_USER");
    const [enabled, setEnabled] = useState(true);
    const [tolerance, setTolerance] = useState("MODERATE");
    const [zipcode, setZipcode] = useState("");

    function validateForm() {
        return username.length > 0 && password.length > 0;
    }
    function validateZip() {
        return postcodeValidator(zipcode, "US");

      }

    async function userRegister(user) {
        return fetch('http://localhost:8080/register/' + zipcode, {
        method: 'POST',
        headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    })
     .then(data =>  {
      if (data.ok) {
        return data.json();
      }
      throw new Error("Something went wrong");
    })
     .catch((error) => {
      console.log(error);
    });
    }

    async function handleSubmit(event) {
        event.preventDefault();
    
        setIsLoading(true);
    
        try {
            const res = await userRegister({username, password, role, enabled, tolerance});
            userRegistered(true);
        }   catch (e) {
            onError(e);
            setIsLoading(false);
        }
      }

      const handleChange = (e) => {
        e.persist();
        setTolerance(e.target.value)
        console.log(e.target.value);
      }

      

  return (
    <div className="Login">
      <Form onSubmit={handleSubmit}>
        <Form.Group size="lg" controlId="username">
          <Form.Label>Username</Form.Label>
          <Form.Control
            autoFocus
            type="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </Form.Group>
        <Form.Group size="lg" controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>
        <Form.Group size="xl" controlId="tolerance">
            <Form.Label>Temperature Preference (Farenheit)</Form.Label>
        {['radio'].map((type) => (
        <div key={`inline-${type}`} className="mb-3">
          <Form.Check
            inline
            label="Very Cold [0 - 25] "
            value="VERY_COLD"
            name="group1"
            type={type}
            id={`inline-${type}-1`}
            onChange={handleChange}
          />
          <Form.Check
            inline
            label="Cold [26 - 55]"
            value="COLD"
            name="group1"
            type={type}
            id={`inline-${type}-2`}
            onChange={handleChange}
          />
          <Form.Check
            inline
            label="Moderate [56 - 68]"
            value="MODERATE"
            name="group1"
            type={type}
            id={`inline-${type}-3`}
            onChange={handleChange}
          />
          <Form.Check
            inline
            label="Hot [69 - 85]"
            value="HOT"
            name="group1"
            type={type}
            id={`inline-${type}-4`}
            onChange={handleChange}
          />
          <Form.Check
            inline
            label="Very Hot [86 - 100]"
            value="VERY_HOT"
            name="group1"
            type={type}
            id={`inline-${type}-5`}
            onChange={handleChange}
          />
        </div>
      ))}
        </Form.Group>
        <Form.Group size="lg" controlId="location">
          <Form.Label>Zipcode</Form.Label>
          <Form.Control
            type="location"
            value={zipcode}
            onChange={(e) => setZipcode(e.target.value)}
          />
        </Form.Group>

        <LoaderButton
            block="true"
            size="lg"
            type="submit"
            isLoading={isLoading}
            disabled={!validateForm() && !validateZip()}
        >
            Register
        </LoaderButton>
      </Form>
    </div>
  )
}

