import React, { useState, useRef } from "react";
import "./LoginSignup.css";

import user_icon from "../../assets/person.png";
import password_icon from "../../assets/password.png";

const LoginSignup = ({ userDatabase, onLogin, setUserDatabase }) => {
	const [action, setAction] = useState("Sign Up");
	const [resultMessage, setResultMessage] = useState(
		"Enter username and password."
	);
	const usernameRef = useRef(null);
	const passwordRef = useRef(null);

	const handleSignUp = () => {
		const enteredUsername = usernameRef.current.value;
		const enteredPassword = passwordRef.current.value;

		if (enteredUsername && enteredPassword) {
			console.log("Entered Username:", enteredUsername);
			console.log("Entered Password:", enteredPassword);

			if (userDatabase[enteredUsername]) {
				setResultMessage("Sign up failed. User already exists.");
				console.log("Username already exists.");
			} else {
				setUserDatabase((prevUserDatabase) => ({
					...prevUserDatabase,
					[enteredUsername]: enteredPassword,
				}));
				//userDatabase[enteredUsername] = enteredPassword;
				setResultMessage("Registration successful.");
				console.log("User registered.");
			}
		}
	};

	const handleLogin = () => {
		const enteredUsername = usernameRef.current.value;
		const enteredPassword = passwordRef.current.value;

		if (enteredUsername && enteredPassword) {
			if (userDatabase[enteredUsername]) {
				if (userDatabase[enteredUsername] === enteredPassword) {
					setResultMessage("Login successful.");
					console.log("Login successful.");
					onLogin(enteredUsername);
				} else {
					setResultMessage("Login failed. Incorrect password.");
					console.log("Incorrect password.");
				}
			} else {
				setResultMessage("Login failed. Username not found.");
				console.log("Username not found.");
			}
		}
	};

	return (
		<div className="authorization-container">
			<div className="header">
				<div className="text">{action}</div>
			</div>
			<div className="inputs">
				<div className="input">
					<img src={user_icon} alt="" />
					<input
						ref={usernameRef}
						type="text"
						placeholder="Username"
					/>
				</div>
				<div className="input">
					<img src={password_icon} alt="" />
					<input
						ref={passwordRef}
						type="password"
						placeholder="Password"
					/>
				</div>
			</div>
			{resultMessage && (
				<div className="result-message">{resultMessage}</div>
			)}
			<div className="submit-container">
				<div
					className={action === "Login" ? "submit gray" : "submit"}
					onClick={() => {
						setAction("Sign Up");
						handleSignUp();
					}}
				>
					Sign Up
				</div>
				<div
					className={action === "Sign Up" ? "submit gray" : "submit"}
					onClick={() => {
						setAction("Login");
						handleLogin();
					}}
				>
					Login
				</div>
			</div>
		</div>
	);
};

export default LoginSignup;
