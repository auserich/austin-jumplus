import React, { useState } from 'react';
import './Login.css';
import LoaderButton from "../components/LoaderButton";
import { useAppContext } from "../lib/contextLib";
import { onError } from "../lib/errorLib";
import Form from "react-bootstrap/Form";

export default function Login() {

  const [isLoading, setIsLoading] = useState(false);
  const { userHasAuthenticated, setToken, setUser } = useAppContext();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function validateForm() {
    return username.length > 0 && password.length > 0;
  }

  async function userLogin(credentials) {
    return fetch('http://localhost:8080/authenticate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
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

   async function getUser(token) {
    return fetch('http://localhost:8080/api/user/whoami', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token.jwt
      }
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

   async function handleSubmit(event) {
    event.preventDefault();

    setIsLoading(true);

    try {
        const token = await userLogin({username, password});
        const user = await getUser(token);
        userHasAuthenticated(true);
        setToken(token);
        console.log(user)
        setUser(user)
    }   catch (e) {
        onError(e);
        setIsLoading(false);
    }
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
        <LoaderButton
            block="true"
            size="lg"
            type="submit"
            isLoading={isLoading}
            disabled={!validateForm()}
        >
            Login
        </LoaderButton>
      </Form>
    </div>
  );
}