import React, { Component } from 'react';
import './App.css';
import { withRouter, Route, Switch } from "react-router-dom";
import {Login} from "./anagrafica_components/Login";
import {USER_TYPE} from "./anagrafica_components/common/Constants";
import {RicercaClienti} from "./anagrafica_components/RicercaClienti";
import {ImportaClienti} from "./anagrafica_components/ImportaClienti";
import OperazioneCompletata from './anagrafica_components/OperazioneCompletata';
import axios from "axios";
import config from "./config.json";
import dotenv from "dotenv";

dotenv.config();

class App extends Component {
  state = { 
    userType: null,
    username: "",
    roles: []
   }

   componentWillMount() {
     if(this.props.location === "" || this.props.location === "/")
    this.props.history.push("/login");

    for (let api in config) {
      config[api] = config[api].replace(
        "[REACT_APP_URL_JAVA]",
        process.env.REACT_APP_URL_JAVA
      );
    }
    // this.handleCheckToken(this.props.location.pathname);
   }

  handleLogin = (loginRequest) => {
    const headers = { "Content-Type": "application/json"};
    const conf = { headers: { ...headers } };

    let roles = [];

    axios.post(config.apiLoginEndpoint, loginRequest, conf)
    .then(response => {console.log(response)
        roles = [...response.data.roles];
        this.setState({roles: roles, username: response.data.username, userType: roles.length === 1 && roles[0].authority === USER_TYPE.USER ? USER_TYPE.USER : USER_TYPE.ADMINISTRATOR})
        console.log(roles[0].authority, roles[0])
        if(roles.length === 1 && roles[0].authority === USER_TYPE.USER){
          this.props.history.push("/ricerca-clienti");
        } else {
          this.props.history.push("/importa-clienti");
        }
     }
    )
    .catch(err => console.log(err));
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
                render={(props) => <RicercaClienti {...props} handleFindCliente={this.handleFindCliente} username={this.state.username} userType={this.state.userType}/>}
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
