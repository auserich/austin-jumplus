function registerUser(username, password, userDatabase) {
	if (userDatabase[username]) {
		console.log("Username already exists.");
		return false;
	} else {
		userDatabase[username] = password;
		console.log("User registered.");
		return true;
	}
}
