import useDate from '../../utils/useDate.js';


export function CurrentTime(props) {

	let now = useDate(props.locale);

	return (
		<div>
			<div>{now.date}</div>
			<div>{now.time}</div>
		</div>
	);
	
}
export default CurrentTime;