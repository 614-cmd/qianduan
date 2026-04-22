export default {
	mounted(el) {
		el.addEventListener("click", () => {
			el.classList.add("is-disabled");
			el.disabled = true;
			setTimeout(() => {
				el.disabled = false;
				el.classList.remove("is-disabled");
			}, 2000);
		});
	},
};
