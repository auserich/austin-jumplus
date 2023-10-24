import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { onError } from "../lib/errorLib";
import "./Settings.css"

export default function Settings() {
  const nav = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

//   function billUser(details) {
//     return API.post("notes", "/billing", {
//       body: details,
//     });
//   }

function renderLander() {
  return (
    <div className="lander">
      <h1>NOT IMPLEMENTED</h1>
      <p className="text-muted">Coming in future update</p>
      <br/>
      <br/>
      <p>Please do back to "Home"</p>
    </div>
  );
}

  return (
    <div className="Settings">
    {renderLander()}
    </div>
  );
}