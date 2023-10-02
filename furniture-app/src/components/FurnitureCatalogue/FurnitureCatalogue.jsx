import React from "react";
import "./FurnitureCatalogue.css";

const FurnitureCatalogue = ({ inventory, addToCart, searchQuery }) => {
	const filteredInventory = inventory.filter((item) =>
		item.name.toLowerCase().includes(searchQuery.toLowerCase())
	);

	return (
		<div className="container">
			<div className="header">
				<div className="catalogue-text">"Good As New" Catalogue</div>
			</div>
			<div className="furniture-container">
				{filteredInventory.length === 0 ? (
					<div className="no-results">No Results</div>
				) : (
					filteredInventory.map((item, index) => (
						<div key={index} className="card">
							<div className="card-img-container">
								<img
									src={item.img} // Use the image URL from the inventory item
									className="card-img-top"
									alt=""
								/>
								<button
									className="add-to-cart-button"
									onClick={() =>
										addToCart(item.name, item.price)
									}
								>
									Add to Cart
								</button>
							</div>
							<div className="card-body">
								<h5 className="card-title">{item.name}</h5>
								<p className="card-text">
									Price: ${item.price}
								</p>
							</div>
						</div>
					))
				)}
			</div>
		</div>
	);
};

export default FurnitureCatalogue;
