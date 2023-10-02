import React from "react";
import "./ShoppingCart.css";

const ShoppingCart = ({
	shoppingCartContents,
	shoppingCartValue,
	onClearCart,
	onConfirmOrder,
	onRemoveItem,
	furnitureImages,
}) => {
	// Check if the cart is empty
	const isCartEmpty = shoppingCartContents.length === 0;

	return (
		<div className="container">
			<div className="header">
				<div className="text">Your Cart</div>
			</div>
			<div className="total">
				Total Value: ${shoppingCartValue.toFixed(2)}
			</div>
			<div className="cart-item-container">
				{shoppingCartContents.length > 0 ? (
					shoppingCartContents.map((item, index) => (
						<div key={index} className="cart-item">
							<div className="cart-item-image">
								<img
									src={furnitureImages[item.name]}
									alt={item.name}
									width="100"
									height="100"
								/>
							</div>
							<div className="cart-item-details">
								<span className="cart-item-name">
									{item.name}
								</span>
								<span className="cart-item-price">
									Price: ${item.price.toFixed(2)}
								</span>
							</div>
							<span
								className="cart-item-remove"
								onClick={() => onRemoveItem(index)}
							>
								X
							</span>
						</div>
					))
				) : (
					<div className="empty-cart">
						<div className="cart-item">
							<span className="cart-item-name">
								Your Cart is empty.
							</span>
						</div>
					</div>
				)}
			</div>

			<div className="button-container">
				<p className="clear-cart" onClick={onClearCart}>
					Clear Cart
				</p>
				<button
					onClick={onConfirmOrder}
					className={`cart-button ${isCartEmpty ? "disabled" : ""}`} // Add "disabled" class
					disabled={isCartEmpty} // Set the "disabled" attribute
				>
					Confirm Order
				</button>
			</div>
		</div>
	);
};

export default ShoppingCart;
