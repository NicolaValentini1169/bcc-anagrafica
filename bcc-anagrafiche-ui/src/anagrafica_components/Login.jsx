import React, { Component } from 'react';

export class Login extends Component {
    state = {  
        login:{
            username: "",
            password: ""
        }
    }

    onChange = (e) => {
        let login = {...this.state.login};
        login[e.currentTarget.name] = e.currentTarget.value;

        this.setState({login});
    }

    render() { 
        return ( 
        <div className="backgroundLogin">
        <div className="container">
            <div className="card card-container">
                <div className="login-icon">
                <i className="fa fa-user fa-5x" />
                </div>
                    <form className="form-signin">
                        <span id="reauth-email" className="reauth-email"></span>
                        <input className="form-control formUser" placeholder="Username" name="username" value={this.state.login.username} onChange={(e) => this.onChange(e)} required autoFocus />
                        <input type="password" className="form-control formUser" placeholder="Password" name="password" value={this.state.login.password} onChange={(e) => this.onChange(e)} required />
                    </form>
                    <div className="button-container">
                    <button className="btn btn-lg btn-success btn-block btn-signin btn-login" type="button" onClick={() => this.props.handleLogin(this.state.login)}>Login</button>
                    </div>
            </div>
        </div>
        </div>
         );
    }
}
 