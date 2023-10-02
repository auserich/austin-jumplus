import React, { useState } from "react";
import Navbar from "./components/Navbar/Navbar";
import FurnitureCatalogue from "./components/FurnitureCatalogue/FurnitureCatalogue";
import LoginSignup from "./components/LoginSignUp/LoginSignUp";
import ShoppingCart from "./components/ShoppingCart/ShoppingCart";
import OrderHistory from "./components/OrderHistory/OrderHistory";

import ottoman_img from "./assets/furniture/ottoman.png";
import etagere_img from "./assets/furniture/etagere.png";
import diptych_arch_img from "./assets/furniture/diptych-art.png";
import day_bed_img from "./assets/furniture/day-bed.png";
import buffet_img from "./assets/furniture/buffet.png";
import credenza_img from "./assets/furniture/credenza.png";
import architrave_img from "./assets/furniture/architrave.png";
import armoire_img from "./assets/furniture/armoire.png";
import flip_top_img from "./assets/furniture/flip-top.png";
import hassock_img from "./assets/furniture/hassock.png";
import jardiniere_img from "./assets/furniture/jardiniere.png";
import lowboy_img from "./assets/furniture/lowboy.png";

function App() {
	const [isLoggedIn, setIsLoggedIn] = useState(false);
	const [userDatabase, setUserDatabase] = useState({});
	const [shoppingCartContents, setShoppingCartContents] = useState([]);
	const [shoppingCartValue, setShoppingCartValue] = useState(0);
	const [cartItemCount, setCartItemCount] = useState(0);
	const [orderHistory, setOrderHistory] = useState({});
	const [showShoppingCart, setShowShoppingCart] = useState(false);
	const [showFurnitureCatalogue, setShowFurnitureCatalogue] = useState(true);
	const [loggedInUsername, setLoggedInUsername] = useState("");
	const [showOrderHistory, setShowOrderHistory] = useState(false);
	const [searchQuery, setSearchQuery] = useState("");
	const [activePage, setActivePage] = useState("catalogue");

	const handleLogin = (username) => {
		setIsLoggedIn(true);
		setLoggedInUsername(username);
		setActivePage("catalogue");
		// setShowFurnitureCatalogue(true);
		// setShowShoppingCart(false);
		// setShowOrderHistory(false);
	};

	const handleSignOut = () => {
		setIsLoggedIn(false);
	};

	const addToCart = (itemName, price) => {
		// Create an object representing the item with name and price
		const newItem = { name: itemName, price: price };
		setShoppingCartContents([...shoppingCartContents, newItem]);
		setShoppingCartValue(shoppingCartValue + price);
		setCartItemCount(cartItemCount + 1);
	};

	const removeItemFromCart = (index) => {
		const updatedCart = [...shoppingCartContents];
		const removedItem = updatedCart.splice(index, 1)[0]; // Remove the item at the specified index
		const updatedCartValue = shoppingCartValue - removedItem.price;

		setShoppingCartContents(updatedCart);
		setShoppingCartValue(updatedCartValue);
		setCartItemCount(cartItemCount - 1);
	};

	const clearCart = () => {
		setShoppingCartContents([]);
		setShoppingCartValue(0);
		setCartItemCount(0);
	};

	const toggleShoppingCart = () => {
		setShowShoppingCart(true);
		setShowFurnitureCatalogue(false);
		setShowOrderHistory(false);
	};

	const handleSearch = (query) => {
		setSearchQuery(query);
	};

	const confirmOrder = () => {
		if (isLoggedIn) {
			const userName = loggedInUsername; // Replace with the actual user's name
			const orderItems = shoppingCartContents.map((item) => item.name); // Extract item names
			const orderTotal = shoppingCartValue; // Calculate the total cost
			if (!orderHistory[userName]) {
				orderHistory[userName] = [];
			}
			orderHistory[userName].push({
				items: orderItems,
				total: orderTotal,
			}); // Store items and total cost in the order
			setOrderHistory({ ...orderHistory });
			clearCart();
		} else {
			alert("Please log in to confirm your order.");
		}
	};

	const handleShowFurnitureCatalogue = () => {
		setShowShoppingCart(false);
		setShowOrderHistory(false); // Hide OrderHistory when FurnitureCatalogue is clicked
	};

	const handleShowOrderHistory = () => {
		setShowOrderHistory(true);
		setShowFurnitureCatalogue(false);
		setShowShoppingCart(false);
	};

	const furnitureImages = {
		Ottoman: ottoman_img,
		Etagere: etagere_img,
		"Diptych Art": diptych_arch_img,
		Daybed: day_bed_img,
		Buffet: buffet_img,
		Credenza: credenza_img,
		Architrave: architrave_img,
		Armoire: armoire_img,
		"Flip-Top": flip_top_img,
		Hassock: hassock_img,
		Jardiniere: jardiniere_img,
		Lowboy: lowboy_img,
	};

	const inventory = [
		{
			name: "Ottoman",
			price: 5.0,
			img: ottoman_img,
		},
		{
			name: "Etagere",
			price: 13.0,
			img: etagere_img,
		},
		{
			name: "Diptych Art",
			price: 25.0,
			img: diptych_arch_img,
		},
		{
			name: "Daybed",
			price: 10.0,
			img: day_bed_img,
		},
		{
			name: "Buffet",
			price: 13.5,
			img: buffet_img,
		},
		{
			name: "Credenza",
			price: 23.0,
			img: credenza_img,
		},
		{
			name: "Architrave",
			price: 5.0,
			img: architrave_img,
		},
		{
			name: "Armoire",
			price: 35.0,
			img: armoire_img,
		},
		{
			name: "Flip-Top",
			price: 7.5,
			img: flip_top_img,
		},
		{
			name: "Hassock",
			price: 18.0,
			img: hassock_img,
		},
		{
			name: "Jardiniere",
			price: 27.0,
			img: jardiniere_img,
		},
		{
			name: "Lowboy",
			price: 15.0,
			img: lowboy_img,
		},
	];

	return (
		<>
			<div className="App">
				<div>
					{isLoggedIn && (
						<>
							<Navbar
								isLoggedIn={isLoggedIn}
								onSignOut={handleSignOut}
								cartItemCount={cartItemCount}
								toggleShoppingCart={() => setActivePage("cart")}
								activePage={activePage}
								onShowFurnitureCatalogue={() =>
									setActivePage("catalogue")
								}
								onShowOrderHistory={() =>
									setActivePage("history")
								}
								onSearch={handleSearch}
								searchQuery={searchQuery}
							/>
							{activePage === "cart" ? (
								<ShoppingCart
									shoppingCartContents={shoppingCartContents}
									shoppingCartValue={shoppingCartValue}
									onClearCart={clearCart}
									onConfirmOrder={confirmOrder}
									onRemoveItem={removeItemFromCart}
									furnitureImages={furnitureImages}
								/>
							) : activePage === "history" ? (
								<OrderHistory
									orderHistory={orderHistory}
									loggedInUsername={loggedInUsername}
								/>
							) : (
								<FurnitureCatalogue
									inventory={inventory}
									addToCart={addToCart}
									searchQuery={searchQuery}
								/>
							)}
						</>
					)}
				</div>

				{!isLoggedIn && (
					<LoginSignup
						userDatabase={userDatabase}
						onLogin={handleLogin}
						setUserDatabase={setUserDatabase}
					/>
				)}
			</div>
		</>
	);
}

export default App;
