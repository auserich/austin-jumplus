import { useState } from "react";

function ListGroup({ items, heading }) {
	const [selectedIndex, setSelectedIndex] = useState(-1);

	const handleClick = (e) => console.log(e);

	return (
		<>
			<h1>{heading}</h1>
			{items.length === 0 && <p>No item found</p>}
			<ul class="list-group">
				{items.map((item, index) => (
					<li
						className={
							selectedIndex === index
								? "list-group-item active"
								: "list-group-item"
						}
						key={item}
						onClick={() => {
							setSelectedIndex(index);
						}}
					>
						{item}
					</li>
				))}
			</ul>
		</>
	);
}

export default ListGroup;
