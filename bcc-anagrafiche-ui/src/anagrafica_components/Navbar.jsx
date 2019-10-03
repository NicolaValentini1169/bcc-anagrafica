import React, { Component } from 'react';
import {LABELS} from "./common/Constants";

class Navbar extends Component {
    state = {  }
    render() { 
        return (
     <nav className="navbar navbar-light bg-light">
        <span className="navbar-brand mb-0 h1">NOME UTENTE</span>
        <button className="btn btn-danger">{LABELS.LOGOUT}</button>
    </nav> );
    }
}
 
export default Navbar;