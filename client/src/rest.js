import { serverAddress } from "./constants";

const createUser = (user) => {
	fetch(serverAddress + "/auth/register", {
		method: "POST",
		body: JSON.stringify({
			firstName: user.firstName,
			lastName: user.lastName,
			email: user.email,
			password: user.password,
		}),
		headers: {
			"Content-Type": "application/json",
		},
	});
};

export { createUser };
