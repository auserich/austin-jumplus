import React, { useState, useEffect } from 'react';

import './App.css';
import { useNavigate } from "react-router-dom";
import { AppContext } from "./lib/contextLib";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Routes from "./Routes";
import Container from 'react-bootstrap/Container';
import { LinkContainer } from "react-router-bootstrap";

function App() {
  const nav = useNavigate();
  const [isAuthenticating, setIsAuthenticating] = useState(true);
  const [isAuthenticated, userHasAuthenticated] = useState(false);
  const [isRegistered, userRegistered] = useState(false);
  const [user, setUser] = useState("")
  const [token, setToken] = useState("");

  useEffect(() => {
    onLoad();
  }, []);
  
  async function onLoad() {
    try {
      if (isRegistered) {
        userRegistered(false);
      }
      await loggedIn();
      userHasAuthenticated(true);
    } catch (e) {
      // if (e !== "No current user") {
      //   alert(e);
      // }
    }
  
    setIsAuthenticating(false);
  }

  async function loggedIn() {
    if (!token) {
      throw new Error("NOT LOGGED IN");
    }

  }
  

  function handleLogout() {
    userHasAuthenticated(false);
    userRegistered(false)
    setToken("");
    setUser("")
    nav("/login");
    //setIsAuthenticating(true);
  }

  sessionStorage.clear();

  return(
    !isAuthenticating && (
      <>
        <Navbar collapseOnSelect bg="secondary opacity-75" data-bs-theme="dark" expand="md" className="mb-3">
          <Container>
          <LinkContainer to="/">
            <Navbar.Brand>
              Weather App
            </Navbar.Brand>
          </LinkContainer>
          <Navbar.Toggle />
          <Navbar.Collapse className="justify-content-end">
            <Nav activeKey={window.location.pathname}>
            {isAuthenticated ? (
              <>
                <LinkContainer to="/settings">
                  <Nav.Link>Settings</Nav.Link>
                </LinkContainer>
                <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
              </>
            ) : (
              <>
                <LinkContainer to="/login">
                  <Nav.Link>Login</Nav.Link>
                </LinkContainer>
                <LinkContainer to="/register">
                  <Nav.Link>Register</Nav.Link>
                </LinkContainer>
              </>

            )}
            </Nav>
          </Navbar.Collapse>
          </Container>
        </Navbar>

        <AppContext.Provider value={{ isAuthenticated, userHasAuthenticated, token, setToken, isRegistered, userRegistered, user, setUser }}>
          <Routes />
        </AppContext.Provider>
    </>
    )
  );
}

export default App;
