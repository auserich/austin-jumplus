import React from "react";
import "./Navbar.css";

import cart_icon from "../../assets/cart.png";
import history_icon from "../../assets/history.png";
import user_icon from "../../assets/person.png";

const Navbar = ({
	isLoggedIn,
	onSignOut,
	cartItemCount,
	toggleShoppingCart,
	onShowFurnitureCatalogue,
	onShowOrderHistory,
	onSearch,
	searchQuery,
}) => {
	const cartIconStyle = {
		position: "relative",
	};

	const itemCountStyle = {
		position: "absolute",
		top: "0",
		right: "50px",
		transform: "translate(50%, -50%)",
		background: "red",
		color: "white",
		borderRadius: "50%",
		padding: "3px 6px",
		fontSize: "12px",
	};

	const handleSearchInputChange = (event) => {
		onSearch(event.target.value);
	};

	return (
		<div class="navbar-container fixed-top">
			<nav className="navbar navbar-expand-lg">
				<div className="container-fluid">
					<div
						className="navbar-brand"
						onClick={onShowFurnitureCatalogue}
					>
						Austin's "Good As New" Merchandise
					</div>
					<form className="d-flex mx-auto">
						<input
							className="form-control me-2"
							type="search"
							placeholder="Search"
							aria-label="Search"
							style={{ width: "500px" }}
							value={searchQuery}
							onChange={handleSearchInputChange}
						/>
						<button
							className="btn btn-outline-success"
							type="submit"
						>
							Search
						</button>
					</form>
					<button
						className="navbar-toggler"
						type="button"
						data-bs-toggle="collapse"
						data-bs-target="#navbarNav"
						aria-controls="navbarNav"
						aria-expanded="false"
						aria-label="Toggle navigation"
					>
						<span className="navbar-toggler-icon"></span>
					</button>
					<div className="collapse navbar-collapse" id="navbarNav">
						<ul className="navbar-nav ms-auto">
							<li className="nav-item">
								<a
									className="nav-link"
									href="#"
									onClick={onSignOut}
								>
									Sign Out
								</a>
							</li>
							<li className="nav-item">
								<a
									className="nav-link"
									href="#"
									onClick={onShowOrderHistory}
								>
									<img
										src={history_icon}
										width="20px"
										height="20px"
										alt="Order History"
									/>
									Order History
								</a>
							</li>
							<li className="nav-item">
								<a
									className="nav-link"
									href="#"
									onClick={toggleShoppingCart}
								>
									<div style={cartIconStyle}>
										<img
											src={cart_icon}
											width="20px"
											height="20px"
											alt="Cart"
										/>
										{cartItemCount > 0 && (
											<span
												className="cart-count"
												style={itemCountStyle}
											>
												{cartItemCount}
											</span>
										)}
										Cart
									</div>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
	);
};

export default Navbar;
