import React, { Component } from 'react';
import './App.css';
import { withRouter, Route, Switch } from "react-router-dom";
import {Login} from "./anagrafica_components/Login";
import {USER_TYPE} from "./anagrafica_components/common/Constants";
import {RicercaClienti} from "./anagrafica_components/RicercaClienti";
import {ImportaClienti} from "./anagrafica_components/ImportaClienti";
import OperazioneCompletata from './anagrafica_components/OperazioneCompletata';

class App extends Component {
  state = { 
    userType: null
   }

   componentWillMount() {
     if(this.props.location === "" || this.props.location === "/")
    this.props.history.push("/login");
   }

  handleLogin = (loginValues) => {
    console.log("LOGIN", loginValues);
    //CHIAMATA AXIOS


    if("USER" === USER_TYPE.USER){
      this.props.history.push("/ricerca-clienti");
      this.setState({userType: 1});
    } else {
      this.props.history.push("/importa-clienti");
      this.setState({userType: 0});
    }
  }

  handleFindCliente = (values) => {

    console.log("CLIENTE", values);

  }

  render() { 
    return (
      <div className="App">
         <Switch>
              <Route
                path="/login"
                exact
                render={(props) => <Login {...props} handleLogin={this.handleLogin}/>}
              />
              <Route
                path="/"
                exact
                render={(props) => <Login {...props} handleLogin={this.handleLogin}/>}
              />
              <Route
                path="/ricerca-clienti"
                exact
                render={(props) => <RicercaClienti {...props} handleFindCliente={this.handleFindCliente}/>}
              />
               <Route
                path="/importa-clienti"
                exact
                render={(props) => <ImportaClienti  {...props}/>}
              />
              <Route
                path="/ricerca-completata"
                exact
                render={(props) => <OperazioneCompletata  {...props}/>}
              />
          </Switch>
      </div>
    );
  }
}
 
export default withRouter(App);
