// function Login() {
// 	return (
// 		<>
// 			const username = window.prompt("Enter your username:"); const
// 			password = window.prompt("Enter your password:"); alert("Username: $
// 			{username}\nPassword: ${password}");
// 		</>
// 	);
// }

// export default Login;

function Login(username, password, userDatabase) {
	if (userDatabase[username]) {
		if (userDatabase[username] === password) {
			console.log("Login successful.");
			return true;
		} else {
			console.log("Incorrect password.");
		}
	} else {
		console.log("Username not found.");
	}
	return false;
}
