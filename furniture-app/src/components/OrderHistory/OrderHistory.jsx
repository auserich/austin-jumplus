import React from "react";
import "./OrderHistory.css";

const OrderHistory = ({ orderHistory, loggedInUsername }) => {
	const userOrderHistory = orderHistory[loggedInUsername] || [];

	return (
		<div className="container order-history">
			<div className="header">
				<div className="text">Order History</div>
			</div>
			<div>
				<strong>{loggedInUsername}'s Orders:</strong>
				{userOrderHistory.map((order, index) => (
					<div key={index} className="order-container">
						<div>
							<strong>
								Total Cost: ${order.total.toFixed(2)}
							</strong>
						</div>
						<ul className="list-group">
							{order.items.map((item, itemIndex) => (
								<li className="list-group-item" key={itemIndex}>
									{item}
								</li>
							))}
						</ul>
					</div>
				))}
			</div>
		</div>
	);
};

export default OrderHistory;
