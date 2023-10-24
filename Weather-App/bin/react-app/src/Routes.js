import React from "react";
import { Route, Routes } from "react-router-dom";
import Home from "./components/Home";
import Login from "./components/Login";
import NotFound from "./components/NotFound";
import Settings from "./components/Settings";
import Register from "./components/Register";
import AuthenticatedRoute from "./components/AuthenticatedRoute";
import UnauthenticatedRoute from "./components/UnauthenticatedRoute";
import RegisterRoute from "./components/RegisterRoute";

export default function Links() {
  return (
    <Routes>
    <Route path="/" element={<Home />} />
    <Route path="/login" element={
      <UnauthenticatedRoute>
        <Login />
      </UnauthenticatedRoute>
    }
    />
    <Route path="/register" element={
      <RegisterRoute>
        <Register />
      </RegisterRoute>
    } 
    />
    <Route path="/settings" element={
      <AuthenticatedRoute>
        <Settings />
      </AuthenticatedRoute>
    } 
    />
    <Route path="*" element={<NotFound />} />;

    
  </Routes>
  );
}

